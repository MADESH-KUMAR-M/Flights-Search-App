package com.example.flights.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface airportDao {

    @Query("SELECT * from airports ORDER BY passengers desc")
    fun getALLAirports(): Flow<List<airport>>

    @Query("SELECT * from airports WHERE LOWER(name) LIKE '%' || LOWER(:str) || '%' or LOWER(iata) LIKE LOWER(:str) ORDER BY passengers desc")
    fun getSuggestion(str: String): Flow<List<airport>>

    @Query("""
    SELECT 
        :code AS depart_code,
        :name AS depart,
        a.iata AS arrival_code,
        a.name AS arrival,
        CASE 
            WHEN f.destination_code = a.iata OR f.departure_code = :code THEN 1 
            ELSE 0
        END AS isFavorite
    FROM 
        airports AS a
    LEFT JOIN 
        favorite AS f 
    ON 
        a.iata = f.destination_code AND f.departure_code = :code
    WHERE 
        a.iata != :code
""")
    fun getAllFlights(code: String, name: String): Flow<List<flightData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: favorite)

    @Query("SELECT id FROM favorite WHERE departure_code = :depart_code AND destination_code = :arrival_code LIMIT 1")
    suspend fun favoriteIsExists(depart_code: String, arrival_code: String): Int

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun deleteFavorite(id: Int)

    @Query("""
        SELECT
            f.departure_code as depart_code,
            d.name AS depart,
            f.destination_code as arrival_code,
            a.name AS arrival,
            1 as isFavorite
         FROM
            favorite f
         JOIN
            airports d ON f.destination_code = d.iata
         JOIN
            airports a ON f.departure_code = a.iata
    """)
    fun getAllFavorites(): Flow<List<flightData>>

}