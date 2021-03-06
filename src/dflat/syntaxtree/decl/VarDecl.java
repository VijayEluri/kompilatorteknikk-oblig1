package dflat.syntaxtree.decl;

import bytecode.CodeFile;
import bytecode.CodeStruct;
import bytecode.type.RefType;
import dflat.syntaxtree.type.ClassType;
import dflat.syntaxtree.type.Name;
import dflat.syntaxtree.type.Type;

public class VarDecl extends Decl {

    private Type type;
    private Name name;

    public VarDecl(Type type, Name name) {
        this.type = type;
        this.name = name;
    }

    public String printAst(int indent) {
        return indentTabs(indent) + "(VAR_DECL " + type.printAst(0) +  name.printAst(0) + ")";
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public void checkSemantics() {
        symbolTable.insert(getName(), getType());
    }

    @Override
    public void generateCode(CodeFile codeFile) {
        if(!(type instanceof ClassType)) {
            codeFile.addVariable(name.toString());
            codeFile.updateVariable(name.toString(), type.getByteCodeType());
        }
        else {
            RefType refType = new RefType(codeFile.structNumber(type.getName().toString()));
            codeFile.addVariable(name.toString());
            codeFile.updateVariable(name.toString(), refType);
        }
    }

}