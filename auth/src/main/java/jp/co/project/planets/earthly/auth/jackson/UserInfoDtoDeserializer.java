package jp.co.project.planets.earthly.auth.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.project.planets.earthly.auth.model.dto.UserInfoDto;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Optional;

/**
 * user info dto deserializer
 */
public class UserInfoDtoDeserializer extends JsonDeserializer<UserInfoDto> {

    @Override
    public UserInfoDto deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final var mapper = (ObjectMapper) parser.getCodec();
        final var root = (JsonNode) mapper.readTree(parser);
        final var id = findJsonValue(root, "id");
        final var loginId = findJsonValue(root, "loginId");
        final var password = findJsonValue(root, "password");
        final var name = findJsonValue(root, "name");
        final var lockout = findJsonValue(root, "lockout");
        final var deleted = findJsonValue(root, "deleted");
        return new UserInfoDto(id, loginId, password, name, BooleanUtils.toBoolean(lockout),
                BooleanUtils.toBoolean(deleted));
    }

    /**
     * find json value
     *
     * @param jsonNode
     *         json node
     * @param fieldName
     *         field name
     * @return json value
     */
    private String findJsonValue(final JsonNode jsonNode, final String fieldName) {
        return Optional.ofNullable(jsonNode.findValue(fieldName)).map(JsonNode::asText).orElse(StringUtils.EMPTY);
    }
}
