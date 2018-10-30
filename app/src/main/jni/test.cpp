
##include "JniTest.h"
#include <stdio.h>

JNIEXPORT jstring JNICALL Java_com_project_pan_myproject_jni_JniTest_get
  (JNIEnv *env, jobject thiz){
    printf("invoke get from c++");
    return env->newStringUTF("hello from jni ");
  }

JNIEXPORT void JNICALL Java_com_project_pan_myproject_jni_JniTest_set
(JNIEnv *env, jobject thiz, jstring string){
    printf("invoke set from c++");
    char* str = (char*)env->GetStringUTF(string,NULL);
    printf("%S\n",str);
    env->ReleaseStringUTFChars(string,str)
}