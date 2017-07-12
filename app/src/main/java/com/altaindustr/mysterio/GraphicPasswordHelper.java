package com.altaindustr.mysterio;

/**
 * Created by КАРАТ on 10.07.2017.
 */

public interface GraphicPasswordHelper
{
    //вот эта функция вызывается из окна рисования пароля каждый раз, как
    //происходит движение пальцем
    public void updateGraphicPassword(String passKey);

    //эта вызывается, когда рисование закончилось. Нужна для настройки пароля.
    public void saveGraphicPassword(String passKey);

}
