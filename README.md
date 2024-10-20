*Welcome to my mathematical optimization language repo*

Currently the project is a work in progress which I am contributing to while completing my master's degree in Data Science at Maastricht University. The aim of this project is to develop a programming language capable of simple operations, such as computing symbolic derivatives, and maybe a couple of optimization methods (think of simplex methods, newton, interior point).

The project currently has an Abstract Syntax Tree (AST) to handle more complex operations such as the chain rule, and many trigonometric functions. I am currently developing my own parser and interpreter for the language, so that any standard expression can be converted into a format accepted by my AST (for example think of converting sin(x) into new Sine(value=new Variable(name="x")).

There are currently a number of missing features, such as the ability to compute partial derivatives, and plot functions, among others. Progress will be slow but I aim to get this language to at least a functional state in the (hopefully) near future.

