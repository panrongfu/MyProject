
#include "JniTest.h"
#include <stdio.h>

void callJavaMethod(JNIEnv *env,jobject thiz){
    jclass clazz = env->FindClass("com/project/pan/myproject/jni/JniTestSdk");
    if(clazz == NULL){
        printf("find class JniTestSDK error");
    }
    jmethodID id  = env->GetStaticMethodID(clazz,"methodCalledByJni","(Ljava/lang/String;)V");
    if(id == NULL){
        printf("find method methodCalledByJni error");
    }
    jstring msg = env->NewStringUTF("msg send by callJavaMethod in test.c");

    env->CallStaticVoidMethod(clazz,id,msg);
}

JNIEXPORT jstring JNICALL Java_com_project_pan_myproject_jni_JniTestSdk_get
  (JNIEnv *env, jobject thiz){
    printf("invoke get from c++");
   // callJavaMethod(env,thiz);
    return env->NewStringUTF("hello from jni ");
  }

JNIEXPORT void JNICALL Java_com_project_pan_myproject_jni_JniTestSdk_set
(JNIEnv *env, jobject thiz, jstring string){
    printf("invoke set from c++");
    char* str = (char*)env->GetStringUTFChars(string,NULL);
    printf("%S\n",str);
    env->ReleaseStringUTFChars(string,str);
}
