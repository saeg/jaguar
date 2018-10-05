# Jaguar

[![Build Status](https://travis-ci.org/saeg/jaguar.svg)](https://travis-ci.org/saeg/jaguar) [![Coverage Status](https://coveralls.io/repos/henriquelemos0/jaguar/badge.png?branch=master)](https://coveralls.io/r/henriquelemos0/jaguar?branch=master)

## **J**av**A** covera**G**e fa**U**lt loc**A**lization **R**ank

Jaguar implements the Spectrum-based Fault Localization (SFL) technique for Java programs.

This technique consists of collecting the coverage for each test and calculate a suspicious score for each element (dua or line) of the code, mainly considering how often it ran on failing and passing tests.

It implements 10 known heuristics to calculate such suspicious score.

## Development

First run the 'make prepare' script to import the jacoco dependencie to your local maven repository
```
make prepare
```

Build the whole project
```
make build
```
