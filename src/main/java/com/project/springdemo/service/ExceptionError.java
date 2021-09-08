package com.project.springdemo.service;

public interface ExceptionError {
    int SUCCESSFUL = 0;
    int BAD_REQUEST=400;
    int IMAGE_FILE_IO_EXCEPTION=900;
    int RETRIEVE_IMAGE_FILE_IO_EXCEPTION=800;

    int USER_MODEL_USERNAME_IS_EXIST = 1;
    int USER_MODEL_NOT_FOUND=2;
    int USER_MODEL_PASSWORD_IS_NULL = 3;

    int REQUEST_MODEL_TITLE_IS_NULL = 4;
    int REQUEST_MODEL_NOT_FOUND=5;
}
