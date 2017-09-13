jaguar
======

JAva coveraGe faUlt locAlization Rank

This tool implements the Spectrum-based Fault Localization (SFL) technique for java programs.

This technique consist of colecting the coverage for each test and calculate a suspicious score for each element (dua or line) of the code, mainly considering how often it ran on failing and passing tests.

It implements 10 known heuristics to calculate such suspicous score.

[![Build Status](https://travis-ci.org/saeg/jaguar.svg)](https://travis-ci.org/saeg/jaguar)

[![Coverage Status](https://coveralls.io/repos/saeg/jaguar/badge.png?branch=master)](https://coveralls.io/r/saeg/jaguar?branch=master)
