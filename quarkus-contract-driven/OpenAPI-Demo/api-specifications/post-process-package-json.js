#!/usr/bin/env node

const fs = require('fs');

const targetFile = process.argv[2];

console.log(targetFile);

let packageJson = JSON.parse(fs.readFileSync(targetFile));

packageJson.files = ['dist/'];

fs.writeFileSync(targetFile, JSON.stringify(packageJson, null, 2), {encoding: 'utf8'});
