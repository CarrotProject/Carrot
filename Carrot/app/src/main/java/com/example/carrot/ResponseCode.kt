package com.example.carrot

class ResponseCode {
    companion object {
        const val SUCCESS_GET = 201
        const val SUCCESS_POST = 200
        const val SUCCESS_DELETE = 204

        const val BAD_REQUEST = 400
        const val NOT_FOUND = 404
        const val CONFLICT_POST = 409
    }
}