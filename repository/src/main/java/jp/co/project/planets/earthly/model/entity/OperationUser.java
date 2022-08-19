package jp.co.project.planets.earthly.model.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Embeddable;

import java.io.Serializable;

/**
 * operation user embeddable
 *
 * @param id
 *         ユーザーID
 * @param name
 *         ユーザー名
 * @param isDeleted
 *         削除フラグ
 */
@Embeddable
public record OperationUser(@Column String id, String name, Boolean isDeleted) implements Serializable {
}
