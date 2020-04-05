Swatc -- Simple WAT Compiler
-----------------------------

A language description:
* Literals and variables;
* Variables definitio and assigment: `let x = 42`;
* Variables reassignment: `x = 0`;
* Arithmetic operations: `+`, `-`, `*`, `/`;
* Comparison operators: `>=`, `>`, `<=`, `<`, `==`, `!=`;
* Branching: `if (expr) { code } else { code }`;
* Loops: `while (expr) { code }`.


Compilation to wasm
--------------------

The whole point of this project is to implement a _compiler_ to wasm. However, the easiest way
to accomplish this task is to _translate_ a program to WebAssembly text format and
then run `wat2wasm` utility to gain a wasm-file.
