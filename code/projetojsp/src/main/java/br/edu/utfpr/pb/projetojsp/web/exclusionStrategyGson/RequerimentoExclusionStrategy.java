package br.edu.utfpr.pb.projetojsp.web.exclusionStrategyGson;

import br.edu.utfpr.pb.projetojsp.model.RequerimentoAnexo;
import br.edu.utfpr.pb.projetojsp.model.RequerimentoDisciplina;
import br.edu.utfpr.pb.projetojsp.model.RequerimentoObservacao;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Edson on 29/09/2017.
 */
public class RequerimentoExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return List.class.equals(fieldAttributes.getDeclaredClass()) && (
                RequerimentoDisciplina.class.equals(((ParameterizedType)fieldAttributes.getDeclaredType()).getActualTypeArguments()[0]) ||
                RequerimentoAnexo.class.equals(((ParameterizedType)fieldAttributes.getDeclaredType()).getActualTypeArguments()[0]) ||
                RequerimentoObservacao.class.equals(((ParameterizedType)fieldAttributes.getDeclaredType()).getActualTypeArguments()[0])
        );
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
