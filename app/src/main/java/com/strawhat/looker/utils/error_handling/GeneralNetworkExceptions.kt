package com.strawhat.looker.utils.error_handling

class UnknownErrorException(code: Int, message: String?): Exception(message)

class InvalidDataException(message: String?): Exception(message)

class UnauthorizedException(message: String?): Exception(message)

class BannedException(message: String?): Exception(message)

