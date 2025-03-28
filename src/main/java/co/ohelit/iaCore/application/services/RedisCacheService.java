package co.ohelit.iaCore.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //Guardar valores
    public void saveData(String key, String value){
        //Se guardara la informacion por solo 60 minutos
        redisTemplate.opsForValue().set(key, value, 60, TimeUnit.MINUTES);
    }

    //Obtener valores
    public String getData(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    //Crear un hash
    public String generateUniqueHash(){
        double aletorio = 0;

        while (aletorio == 0){
            aletorio = Math.floor(Math.random()*10000);
            if (getData(String.valueOf(aletorio)) == null){
                aletorio = 0;
            }
        }
        return String.valueOf(aletorio);
    }

}
