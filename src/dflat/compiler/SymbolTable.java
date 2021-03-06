package dflat.compiler;

import dflat.exceptions.SymbolAlreadyDeclaredException;
import dflat.syntaxtree.type.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


/*TODO:
    Hvis man sl�r opp type som er deklarert flere ganger s� kan man risikere � ikke f� semantikkfeil ved aksess av
    variable som er deklarert av klassen utenfor scope. dritt.
  */
public class SymbolTable {
    Stack<HashMap<Name, Type>> scopeStack;
    HashMap<Name, Type> currentScope;

    public SymbolTable() {
        scopeStack = new Stack<HashMap<Name, Type>>();
        currentScope = new HashMap<Name, Type>();
        

        insertBuiltInTypes();
    }

    private void insertBuiltInTypes() {
        insert(new Name("string"), new StringType());
        insert(new Name("int"), new IntegerType());
        insert(new Name("bool"), new BooleanType());
        insert(new Name("float"), new FloatType());
        insert(new Name("void"), new VoidType());
    }


    public Type lookup(Name name) {
        Type typeFromCurrentScope = currentScope.get(name);
        if (typeFromCurrentScope != null) {
            return typeFromCurrentScope;
        }
        //Finnes ikke i n�v�rende skop. sjekker andre skop.
        for(int i = scopeStack.size() - 1; i > -1; i--) {
            Map<Name, Type> scope = scopeStack.get(i);
            Type type = scope.get(name);
            if(type != null) {
                return type;
            }
        }
        return null;
    }

    public void insert(Name name, Type type) {
        if(currentScope.get(name) != null) {
            throw new SymbolAlreadyDeclaredException(name);
        }
        currentScope.put(name, type);
    }

    public void enter_scope() {
        HashMap<Name, Type> oldScope = (HashMap<Name, Type>) currentScope.clone();
        scopeStack.push(oldScope);
        currentScope = new HashMap<Name, Type>();
    }

    public void exit_scope() {
        currentScope = scopeStack.pop();
    }


    @Override
    public String toString() { //for debug
        StringBuilder sb = new StringBuilder();
        sb.append("Current scope: \n");
        for (Name name : currentScope.keySet()) {
            sb.append(name.toString());
            sb.append("\n");
        }
        for (Map<Name, Type> table : scopeStack) {
            sb.append("\nScope: \n");
            for (Name name : table.keySet()) {
                sb.append(name.toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}