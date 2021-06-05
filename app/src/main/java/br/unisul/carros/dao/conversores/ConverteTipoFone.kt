package br.unisul.carros.dao.conversores

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import br.unisul.carros.model.enuns.TipoFone

class ConverteTipoFone {

    @TypeConverter
    fun paraString(tipoFone: TipoFone): String{
        return tipoFone.name
    }

    @TypeConverter
    fun paraTipoFone(tipoFoneString: String?): TipoFone{
        return if (tipoFoneString != null) {
            TipoFone.valueOf(tipoFoneString)
        } else {
            TipoFone.RESIDENCIAL
        }
    }
}