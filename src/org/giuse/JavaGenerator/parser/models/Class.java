package org.giuse.JavaGenerator.parser.models;

import java.util.ArrayList;

public class Class extends Struct{
    private String Extends;
    private ArrayList<String> Implements;
    private Boolean isAbstract;

    private Class(String pathname, Boolean isAbstract ,String scope, String name, String anExtends, ArrayList<String> anImplements, ArrayList<Attribute> attributes, ArrayList<Function> functions) {
        super(pathname, scope, name, attributes, functions);
        Extends = anExtends;
        Implements = anImplements;
        this.isAbstract = isAbstract;
    }

    public Boolean getAbstract() {
        return isAbstract;
    }

    public void setAbstract(Boolean anAbstract) {
        isAbstract = anAbstract;
    }

    public String getExtends() {
        return Extends;
    }

    public void setExtends(String anExtends) {
        Extends = anExtends;
    }

    public ArrayList<String> getImplements() {
        return Implements;
    }

    public void setImplements(ArrayList<String> anImplements) {
        Implements = anImplements;
    }

    public String generateContent() {
        StringBuilder classContent = new StringBuilder();

        classContent.append(super.scope).append(" ");

        if(getAbstract())
            classContent.append("abstract").append(" ");

        classContent.append("class").append(" ").append(super.name);

        if(getExtends() != null)
            classContent.append(" extends ").append(getExtends());

        for(int i=0;i<getImplements().size();i++){
            if(i==0)
                classContent.append(" implements ").append(getImplements().get(i));
            else
                classContent.append(", ").append(getImplements().get(i));
        }

        classContent.append("{");

        for(Attribute attribute: super.attributes){
            classContent.append("\n\t");
            classContent.append(attribute.generateContent());
            classContent.append(";\n");
        }

        for(Function function: super.functions){
            classContent.append("\n\t");
            classContent.append(function.generateContent());
            classContent.append("\n");
        }

        classContent.append("}");

        return classContent.toString();
    }

    public static class Builder extends Struct.Builder{

        private Boolean bIsAbstract;
        private String bExtends;
        private final ArrayList<String> bImplements;

        public Builder(String pathname, String scope, String name){
            super(pathname,scope,name);
            this.bImplements = new ArrayList<>();
            this.bIsAbstract = false;
            this.bExtends = "";
        }

        public Builder isAbstract(){
            this.bIsAbstract = true;
            return this;
        }

        public Builder setExtends(String Extends){
            this.bExtends = Extends;
            return this;
        }
        public Builder addImplements(String Implements){
            this.bImplements.add(Implements);
            return this;
        }

        public Class build(){
            return new Class(bPathname, bIsAbstract, bScope, bName, bExtends, bImplements, bAttributes, bFunctions);
        }
    }
}
