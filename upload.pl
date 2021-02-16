#!/usr/bin/perl -Ilib
use Data::Table::Text qw(:all);
use GitHub::Crud;

GitHub::Crud::writeFileFromFileFromCurrentRun q(output.txt);
