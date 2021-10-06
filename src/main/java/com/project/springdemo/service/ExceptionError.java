package com.project.springdemo.service;

public interface ExceptionError {
    int SUCCESSFUL = 0;
    int BAD_REQUEST=400;
    int IMAGE_FILE_IO_EXCEPTION=900;
    int RETRIEVE_IMAGE_FILE_IO_EXCEPTION=800;

    int USER_MODEL_USERNAME_IS_EXIST = 1;
    int USER_MODEL_NOT_FOUND=2;
    int USER_MODEL_PASSWORD_IS_NULL = 3;
    int USER_VERIFY_CODE_IS_WRONG = 4;
    int USER_TOS_IS_REQUIRED = 5;

    int REQUEST_MODEL_TITLE_IS_NULL = 6;
    int REQUEST_MODEL_NOT_FOUND=7;

    int CATEGORY_MODEL_NOT_FOUND=8;
}
