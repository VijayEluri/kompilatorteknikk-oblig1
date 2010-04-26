package dflat.syntaxtree.expression;

import dflat.syntaxtree.type.Type;

public class NewExpression extends Expression {

	private Type type;
	public NewExpression(Type type) {
		this.type = type;
	}
	@Override
	public String printAst(int indent) {
		return indentTabs(indent) + "(NEW " + type.printAst(0) + ")";
	}

}