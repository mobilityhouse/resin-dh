#!/usr/bin/env python

""" From https://github.com/kergoth/bb/blob/master/libexec/bbcmd.py """

import argparse
import contextlib
import logging
import os
import sys
import warnings

PATH = os.getenv('PATH').split(':')
bitbake_paths = [os.path.join(path, '..', 'lib')
                 for path in PATH if os.path.exists(os.path.join(path, 'bitbake'))]
if not bitbake_paths:
    raise ImportError("Unable to locate bitbake, please ensure PATH is set correctly.")

sys.path[0:0] = bitbake_paths

import bb.msg
import bb.utils
import bb.providers
import bb.tinfoil
from bb.cookerdata import CookerConfiguration, ConfigParameters


class Terminate(BaseException):
    pass


class Tinfoil(bb.tinfoil.Tinfoil):
    def __init__(self, output=sys.stdout):
        # Needed to avoid deprecation warnings with python 2.6
        warnings.filterwarnings("ignore", category=DeprecationWarning)

        # Set up logging
        self.logger = logging.getLogger('BitBake')
        if output is not None:
            setup_log_handler(self.logger, output)

        self.config = self.config = CookerConfiguration()
        configparams = bb.tinfoil.TinfoilConfigParameters(parse_only=True)
        self.config.setConfigParameters(configparams)
        self.config.setServerRegIdleCallback(self.register_idle_function)
        self.cooker = bb.cooker.BBCooker(self.config)
        self.config_data = self.cooker.data
        bb.providers.logger.setLevel(logging.ERROR)
        bb.taskdata.logger.setLevel(logging.CRITICAL)
        self.cooker_data = None
        self.taskdata = None

        self.localdata = bb.data.createCopy(self.config_data)
        self.localdata.finalize()
        # TODO: why isn't expandKeys a method of DataSmart?
        bb.data.expandKeys(self.localdata)


    def prepare_taskdata(self, provided=None, rprovided=None):
        self.cache_data = self.cooker.recipecache
        if self.taskdata is None:
            self.taskdata = bb.taskdata.TaskData(abort=False)

        if provided:
            self.add_provided(provided)

        if rprovided:
            self.add_rprovided(rprovided)

    def add_rprovided(self, rprovided):
        for item in rprovided:
            self.taskdata.add_rprovider(self.localdata, self.cache_data, item)

        self.taskdata.add_unresolved(self.localdata, self.cache_data)

    def add_provided(self, provided):
        if 'world' in provided:
            if not self.cache_data.world_target:
                self.cooker.buildWorldTargetList()
            provided.remove('world')
            provided.extend(self.cache_data.world_target)

        if 'universe' in provided:
            provided.remove('universe')
            provided.extend(self.cache_data.universe_target)

        for item in provided:
            self.taskdata.add_provider(self.localdata, self.cache_data, item)

        self.taskdata.add_unresolved(self.localdata, self.cache_data)

    def rec_get_dependees(self, targetid, depth=0, seen=None):
        if seen is None:
            seen = set()

        for dependee_fnid, dependee_id in self.get_dependees(targetid, seen):
            yield dependee_id, depth

            for _id, _depth in self.rec_get_dependees(dependee_id, depth+1, seen):
                yield _id, _depth

    def get_dependees(self, targetid, seen):
        dep_fnids = self.taskdata.get_dependees(targetid)
        for dep_fnid in dep_fnids:
            if dep_fnid in seen:
                continue
            seen.add(dep_fnid)
            for target in self.taskdata.build_targets:
                if dep_fnid in self.taskdata.build_targets[target]:
                    yield dep_fnid, target

    def get_buildid(self, target):
        if not self.taskdata.have_build_target(target):
            if target in self.cooker.recipecache.ignored_dependencies:
                return

            reasons = self.taskdata.get_reasons(target)
            if reasons:
                self.logger.error("No buildable '%s' recipe found:\n%s", target, "\n".join(reasons))
            else:
                self.logger.error("No '%s' recipe found", target)
            return
        else:
            return self.taskdata.getbuild_id(target)

    def target_filenames(self):
        """Return the filenames of all of taskdata's targets"""
        filenames = set()

        for targetid in self.taskdata.build_targets:
            fnid = self.taskdata.build_targets[targetid][0]
            fn = self.taskdata.fn_index[fnid]
            filenames.add(fn)

        for targetid in self.taskdata.run_targets:
            fnid = self.taskdata.run_targets[targetid][0]
            fn = self.taskdata.fn_index[fnid]
            filenames.add(fn)

        return filenames

    def all_filenames(self):
        return self.cooker.recipecache.file_checksums.keys()

    def all_preferred_filenames(self):
        """Return all the recipes we have cached, filtered by providers.

        Unlike target_filenames, this doesn't operate against taskdata.
        """
        filenames = set()
        excluded = set()
        for provide, fns in self.cooker.recipecache.providers.iteritems():
            eligible, foundUnique = bb.providers.filterProviders(fns, provide,
                                                                 self.localdata,
                                                                 self.cooker.recipecache)
            preferred = eligible[0]
            if len(fns) > 1:
                # Excluding non-preferred providers in multiple-provider
                # situations.
                for fn in fns:
                    if fn != preferred:
                        excluded.add(fn)
            filenames.add(preferred)

        filenames -= excluded
        return filenames

    def provide_to_fn(self, provide):
        """Return the preferred recipe for the specified provide"""
        filenames = self.cooker.recipecache.providers[provide]
        eligible, foundUnique = bb.providers.filterProviders(filenames, provide, self.localdata)
        return eligible[0]

    def build_target_to_fn(self, target):
        """Given a target, prepare taskdata and return a filename"""
        self.prepare_taskdata([target])
        targetid = self.get_buildid(target)
        if targetid is None:
            return
        fnid = self.taskdata.build_targets[targetid][0]
        fn = self.taskdata.fn_index[fnid]
        return fn

    def parse_recipe_file(self, recipe_filename):
        """Given a recipe filename, do a full parse of it"""
        appends = self.cooker.collection.get_file_appends(recipe_filename)
        try:
            recipe_data = bb.cache.Cache.loadDataFull(recipe_filename,
                                                      appends,
                                                      self.config_data)
        except Exception:
            raise
        return recipe_data

    def parse_metadata(self, recipe=None):
        """Return metadata, either global or for a particular recipe"""
        if recipe:
            self.prepare_taskdata([recipe])
            filename = self.build_target_to_fn(recipe)
            return self.parse_recipe_file(filename)
        else:
            return self.localdata


class CompleteParser(argparse.ArgumentParser):
    """Argument parser which handles '--complete' for completions"""
    def __init__(self, *args, **kwargs):
        self.complete_parser = argparse.ArgumentParser(add_help=False)
        self.complete_parser.add_argument('--complete', action='store_true')
        super(CompleteParser, self).__init__(*args, **kwargs)

    def parse_args(self, args=None, namespace=None):
        parsed, remaining = self.complete_parser.parse_known_args(args)
        if parsed.complete:
            for action in self._actions:
                for string in action.option_strings:
                    print(string)
        else:
            return super(CompleteParser, self).parse_args(remaining, namespace)


def iter_uniq(iterable):
    """Yield unique elements of an iterable"""
    seen = set()
    for i in iterable:
        if i not in seen:
            seen.add(i)
            yield i


@contextlib.contextmanager
def status(message, outfile=sys.stderr):
    """Show the user what we're doing, and whether we succeed"""
    outfile.write('{0}..'.format(message))
    outfile.flush()
    try:
        yield
    except KeyboardInterrupt:
        outfile.write('.interrupted\n')
        raise
    except Terminate:
        outfile.write('.terminated\n')
        raise
    except BaseException:
        outfile.write('.failed\n')
        raise
    outfile.write('.done\n')


def setup_log_handler(logger, output=sys.stderr):
    log_format = bb.msg.BBLogFormatter("%(levelname)s: %(message)s")
    if output.isatty() and hasattr(log_format, 'enable_color'):
        log_format.enable_color()
    handler = logging.StreamHandler(output)
    handler.setFormatter(log_format)

    bb.msg.addDefaultlogFilter(handler)
    logger.addHandler(handler)
    logger.setLevel(logging.INFO)


def sigterm_exception(signum, stackframe):
    raise Terminate()

###### end of bbcmd

import pickle

def load_data(data_file):
    try:
        fd = open(data_file, 'r')
        data = pickle.load(fd)
        fd.close()
        return data
    except:
        return {}

def dump_data(data, data_file):
    fd = open(data_file, 'w')
    pickle.dump(data, fd)
    fd.close()

def extract_bitbake_metadata(recipes):
    # tinfoil sets up log output for the bitbake loggers, but bb uses
    # a separate namespace at this time
    setup_log_handler(logging.getLogger('bb'))

    tinfoil = Tinfoil(output=sys.stderr)
    tinfoil.prepare(config_only=True)

    tinfoil.parseRecipes()

    data = {}

    metadata = tinfoil.parse_metadata()
    machine = metadata.getVar('MACHINE', True)
    data['image-bootloader'] = metadata.getVar('IMAGE_BOOTLOADER', True)
    data['soc-family'] = metadata.getVar('SOC_FAMILY', True)
    if data['soc-family'] is None:
        data['soc-family'] = metadata.getVar('MACHINEOVERRIDES', True)
    data['recipes'] = {}

    metadata = None
    for recipe in recipes:
        try:
            metadata = tinfoil.parse_metadata(recipe)
        except:
            continue

        pv = metadata.getVar('PV', True)
        localversion = metadata.getVar('LOCALVERSION', True)
        version = pv + (localversion or '')

        data['recipes'][recipe] = {}
        data['recipes'][recipe]['recipe'] = metadata.getVar('PN', True)
        data['recipes'][recipe]['version'] = version
        data['recipes'][recipe]['file'] = tinfoil.build_target_to_fn(recipe)
        data['recipes'][recipe]['srcbranch'] = metadata.getVar('SRCBRANCH', True)
        data['recipes'][recipe]['compatible-machine'] = metadata.getVar('COMPATIBLE_MACHINE', True)

        description = metadata.getVar('DESCRIPTION', True)
        if not description:
            description = metadata.getVar('SUMMARY', True)
        data['recipes'][recipe]['description'] = description

    return {machine: data}

logger = logging.getLogger('bb.dump')

data_file = sys.argv[1]
user_recipes = sys.argv[2:]

data = load_data(data_file)
data.update(extract_bitbake_metadata(user_recipes))

dump_data(data, data_file)
