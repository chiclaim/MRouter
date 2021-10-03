package com.chiclaim.router.plugin.handler;

public interface IUpdate {

    /**
     * 是否修改了 class 字节码
     * @return 是否修改过class字节码
     */
    boolean hadUpdateBytecode();

}
