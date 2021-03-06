package io.github.cdgeass.editor.dom.element.convert;

import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.cache.CacheManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.UsageSearchContext;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.Converter;
import com.intellij.util.xml.CustomReferenceConverter;
import com.intellij.util.xml.GenericDomValue;
import io.github.cdgeass.constants.StringConstants;
import io.github.cdgeass.editor.dom.DomUtil;
import io.github.cdgeass.editor.dom.XmlReference;
import io.github.cdgeass.editor.dom.element.configuration.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author cdgeass
 * @since 2020-06-19
 */
public class AliasReferenceConvert extends Converter<PsiClass> implements CustomReferenceConverter<PsiClass> {

    @Nullable
    @Override
    public PsiClass fromString(@Nullable String s, ConvertContext context) {
        if (s == null) {
            return null;
        }

        var cacheManager = CacheManager.getInstance(context.getProject());
        var psiFiles = cacheManager.getFilesWithWord(StringConstants.TYPE_ALIASES, UsageSearchContext.IN_PLAIN_TEXT,
                GlobalSearchScope.allScope(context.getProject()), true);

        var typeAliasesList = Arrays.stream(psiFiles)
                .filter(psiFile -> psiFile instanceof XmlFile)
                .map(xmlFile -> DomUtil.findFileElement(xmlFile, Configuration.class))
                .filter(Objects::nonNull)
                .map(Configuration::getTypeAliases)
                .collect(Collectors.toList());

        for (var typeAliases : typeAliasesList) {
            for (var typeAlias : typeAliases.getTypeAliases()) {
                var aliasAttributeValue = typeAlias.getAlias();
                var typeAttributeValue = typeAlias.getType();

                var alias = aliasAttributeValue.getValue();
                if (StringUtils.equals(alias, s)) {
                    return typeAttributeValue.getValue();
                }
            }

            for (var aPackage : typeAliases.getPackages()) {
                var nameAttributeValue = aPackage.getName();
                if (nameAttributeValue.getValue() == null) {
                    continue;
                }
                var qualifiedName = nameAttributeValue.getValue() + "." + s;
                var psiClass = JavaPsiFacade.getInstance(context.getProject()).findClass(qualifiedName,
                        GlobalSearchScope.allScope(context.getProject()));
                if (psiClass != null) {
                    return psiClass;
                }
            }
        }

        return null;
    }

    @Nullable
    @Override
    public String toString(@Nullable PsiClass element, ConvertContext context) {
        if (element == null) {
            return null;
        }
        return element.getQualifiedName();
    }

    @NotNull
    @Override
    public PsiReference[] createReferences(GenericDomValue<PsiClass> value, PsiElement element, ConvertContext context) {
        var psiClass = value.getValue();
        if (psiClass == null) {
            return PsiReference.EMPTY_ARRAY;
        }
        return new PsiReference[]{new XmlReference<>(element, Collections.singletonList(psiClass.getNameIdentifier()))};
    }
}
