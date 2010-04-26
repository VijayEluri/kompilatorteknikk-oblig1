package dflat.syntaxtree.expression;

import dflat.syntaxtree.type.Name;

public class VariableExpression extends Expression {

	protected Name name;

	public VariableExpression(Name name) {
		this.name = name;
	}
	
	public String printAst(int indent) {
		return name.printAst(indent);
	}
}