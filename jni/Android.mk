LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_CPP_EXTENSION := .cpp .cc
LOCAL_MODULE    := mcpedumper
LOCAL_SRC_FILES := MCPEDumper.cpp

ifeq ($(TARGET_ARCH_ABI),x86)
    LOCAL_CFLAGS += -ffast-math -mtune=atom -mssse3 -mfpmath=sse
endif
LOCAL_CFLAGS += -fexceptions
include $(BUILD_SHARED_LIBRARY)
