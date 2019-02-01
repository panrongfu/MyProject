
#include "JniSDK.h"
#include <stdio.h>

void callJavaMethod(JNIEnv *env,jobject thiz){
    jclass clazz = env->FindClass("com/project/pan/jnisdk/sdk/JniSDK");
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

JNIEXPORT jstring JNICALL Java_com_project_pan_jnisdk_sdk_JniSDK_get
  (JNIEnv *env, jobject thiz){
    printf("invoke get from c++");
    callJavaMethod(env,thiz);
    return env->NewStringUTF("hello from jni ");
  }

JNIEXPORT void JNICALL Java_com_project_pan_jnisdk_sdk_JniSDK_set
(JNIEnv *env, jobject thiz, jstring string){
    printf("invoke set from c++");
    char* str = (char*)env->GetStringUTFChars(string,NULL);
    printf("%S\n",str);
    env->ReleaseStringUTFChars(string,str);
}
