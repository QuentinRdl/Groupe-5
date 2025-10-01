grammar MiniJaJa;

@header{
import fr.ufrst.m1info.gl.compGL.Nodes.*;
}

classe returns [ClassNode node]
    : 'class' ident '{' decls methmain '}' {$node = new ClassNode($ident.node, $decls.node, $methmain.node);}
    ;

ident returns [ASTNode node]
    : . {$node = null;}
    ;

decls returns [ASTNode node]
    : . {$node = null;}
    ;

methmain returns [ASTNode node]
    : . {$node = null;}
    ;