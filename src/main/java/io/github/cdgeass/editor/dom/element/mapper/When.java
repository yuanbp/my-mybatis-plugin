package io.github.cdgeass.editor.dom.element.mapper;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagList;

import java.util.List;

/**
 * @author cdgeass
 * @since 2020-06-20
 */
public interface When extends DomElement {

    GenericAttributeValue<String> getTest();

    @SubTagList("include")
    List<Include> getIncludes();

    @SubTagList("trim")
    List<Trim> getTrims();

    @SubTagList("where")
    List<Where> getWheres();

    @SubTagList("set")
    List<Set> getSets();

    @SubTagList("foreach")
    List<Foreach> getForeaches();

    @SubTagList("choose")
    List<Choose> getChooses();

    @SubTagList("if")
    List<If> getIfs();

    @SubTagList("bind")
    List<Bind> getBinds();
}
