package io.github.cdgeass.editor;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author cdgeass
 * @since 2020-05-14
 */
public class InterfaceXmlLineMarkerProvider extends RelatedItemLineMarkerProvider {

    // implementedMethod.svg

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {

    }
}