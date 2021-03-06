package dflat.syntaxtree.statement;

import bytecode.CodeProcedure;
import dflat.exceptions.SymbolNotDeclaredException;
import dflat.syntaxtree.Node;
import dflat.syntaxtree.param.ActualParam;
import dflat.syntaxtree.param.FormalParam;
import dflat.syntaxtree.type.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CallStatementTest  {

    @Test(expected = SymbolNotDeclaredException.class)
    public void testThatSyntaxCheckFailsWhenMethodDoesntExist() {
        CallStatement underTest = new CallStatement(new Name("foo"), new ArrayList<ActualParam>());
        underTest.checkSemantics();
    }


    @Test(expected = SymbolNotDeclaredException.class)
    public void testThatSyntaxCheckFaileWhenSignatureIsDifferent() throws Exception {
        List<ActualParam> paramList = new ArrayList<ActualParam>();
        ActualParam param = paramMock(new IntegerType());
        paramList.add(param);
        CallStatement underTest = new CallStatement(new Name("foo"), paramList);

        Node.getSymbolTable().insert(createFunctionName(new StringType()), new VoidType());

        underTest.checkSemantics();


    }



    private FunctionName createFunctionName(Type signature1) {
        List<FormalParam> signatureList = new ArrayList<FormalParam>();
        signatureList.add(new FormalParam(false, signature1, new Name("booFar")));

        return new FunctionName(new Name("foo"), signatureList);
    }


    private ActualParam paramMock(final Type type) {
        return new ActualParam() {
            @Override
            public String printAst(int indent) {
                return null;
            }

            @Override
            public void checkSemantics() {
            }


            public Type getType() {
                return type;
            }

            @Override
            public boolean getIsRef() {
                return false;
            }

            @Override
            public void generateCode(CodeProcedure procedure) {
            }
        };
    }
}
