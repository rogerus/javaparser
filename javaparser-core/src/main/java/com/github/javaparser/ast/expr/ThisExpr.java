/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2016 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser can be used either under the terms of
 * a) the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * b) the terms of the Apache License
 *
 * You should have received a copy of both licenses in LICENCE.LGPL and
 * LICENCE.APACHE. Please refer to those files for details.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */
package com.github.javaparser.ast.expr;

import com.github.javaparser.Range;
import com.github.javaparser.ast.AllFieldsConstructor;
import com.github.javaparser.ast.observer.ObservableProperty;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import java.util.Optional;
import com.github.javaparser.ast.Node;

/**
 * An occurrence of the "this" keyword. <br/><code>World.this.greet()</code> is a MethodCallExpr of method name greet,
 * and scope "World.super" which is a ThisExpr with classExpr "World". <br/><code>this.name</code> is a
 * FieldAccessExpr of field greet, and a ThisExpr as its scope. The ThisExpr has no classExpr.
 *
 * @author Julio Vilmar Gesser
 * @see com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt
 * @see ThisExpr
 */
public final class ThisExpr extends Expression {

    private Expression classExpr;

    public ThisExpr() {
        this(null, null);
    }

    @AllFieldsConstructor
    public ThisExpr(final Expression classExpr) {
        this(null, classExpr);
    }

    public ThisExpr(final Range range, final Expression classExpr) {
        super(range);
        setClassExpr(classExpr);
    }

    @Override
    public <R, A> R accept(final GenericVisitor<R, A> v, final A arg) {
        return v.visit(this, arg);
    }

    @Override
    public <A> void accept(final VoidVisitor<A> v, final A arg) {
        v.visit(this, arg);
    }

    public Optional<Expression> getClassExpr() {
        return Optional.ofNullable(classExpr);
    }

    public ThisExpr setClassExpr(final Expression classExpr) {
        notifyPropertyChange(ObservableProperty.CLASS_EXPR, this.classExpr, classExpr);
        if (this.classExpr != null)
            this.classExpr.setParentNode(null);
        this.classExpr = classExpr;
        setAsParentNodeOf(classExpr);
        return this;
    }

    @Override
    public boolean remove(Node node) {
        if (node == null)
            return false;
        if (classExpr != null) {
            if (node == classExpr) {
                setClassExpr((Expression) null);
                return true;
            }
        }
        return super.remove(node);
    }
}

