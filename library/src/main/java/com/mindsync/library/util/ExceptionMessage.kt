package com.mindsync.mindmap.util

enum class ExceptionMessage(val message: String) {
    ERROR_MESSAGE_INVALID_NODE_ID("Node Id is invalid"),
    ERROR_MESSAGE_DUPLICATED_NODE("Target node is duplicated"),
    ERROR_MESSAGE_TARGET_NODE_NOT_EXIST("Target Node is not exist"),
    ERROR_MESSAGE_PARENT_NODE_NOT_EXIST("Parent Node is not exist"),
    ERROR_MESSAGE_ROOT_NODE_NOT_EXIST("Root Node is not exist"),
    ERROR_MESSAGE_ROOT_CANT_REMOVE("Root can't remove"),
    ERROR_MESSAGE_NOT_DEFINED_OPERATION("Operation is not defined"),
}

enum class NetworkExceptionMessage(val message: String) {
    ERROR_MESSAGE_KAKAO_RESULT_NULL("Kakao Login result is null"),
    ERROR_MESSAGE_CANT_GET_TOKEN("Can't get server access token"),
}

enum class SpaceExceptionMessage(val message: String) {
    ERROR_MESSAGE_SPACE_ADD("Space Add failed"),
    ERROR_MESSAGE_SPACE_INVITE_CODE_WRONG("wrong inviteCode"),
}

enum class ResponseErrorMessage(val message: String) {
    ERROR_MESSAGE_BODY_NULL("Body is empty"),
    ERROR_MESSAGE_RESPONSE_FAIL("Response is failed"),
}
