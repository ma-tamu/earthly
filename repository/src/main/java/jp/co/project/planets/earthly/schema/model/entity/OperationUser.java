package jp.co.project.planets.earthly.schema.model.entity;

import java.io.Serializable;

import org.seasar.doma.Column;
import org.seasar.doma.Embeddable;

/**
 * operation user embeddable
 *
 * @param id
 *            ユーザーID
 * @param name
 *            ユーザー名
 * @param isDeleted
 *            削除フラグ
 */
@Embeddable
public record OperationUser(@Column String id, String name, Boolean isDeleted) implements Serializable {
}
