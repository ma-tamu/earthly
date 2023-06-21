package jp.co.project.planets.earthly.db.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;

/**
 * パスワードトークン
 */
@Entity(listener = PasswordTokenListener.class, metamodel = @Metamodel)
@Table(name = "password_token")
public class PasswordToken extends AbstractPasswordToken implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /** id */
    @Id
    @Column(name = "id")
    String id;

    /** ユーザーID */
    @Column(name = "user_id")
    String userId;

    /** トークン */
    @Column(name = "token")
    String token;

    /** 有効期限 */
    @Column(name = "expire")
    LocalDateTime expire;

    public PasswordToken() {
    }

    /**
     * new instance
     * 
     * @Param id id
     * @Param userId ユーザーID
     * @Param token トークン
     * @Param expire 有効期限
     */
    public PasswordToken(final String id, final String userId, final String token, final LocalDateTime expire) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expire = expire;
    }

    /**
     * Returns the id.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Returns the userId.
     * 
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the userId.
     * 
     * @param userId
     *            the userId
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Returns the token.
     * 
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     * 
     * @param token
     *            the token
     */
    public void setToken(final String token) {
        this.token = token;
    }

    /**
     * Returns the expire.
     * 
     * @return the expire
     */
    public LocalDateTime getExpire() {
        return expire;
    }

    /**
     * Sets the expire.
     * 
     * @param expire
     *            the expire
     */
    public void setExpire(final LocalDateTime expire) {
        this.expire = expire;
    }
}
