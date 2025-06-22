package com.example.orcamento

import retrofit2.http.Headers

interface BaseSupabaseApi {
    companion object {
        const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlyYnpid3J4bmtudW5yY2FpcXlhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTA1NjA1OTEsImV4cCI6MjA2NjEzNjU5MX0._h0bFpfVcEKWBKRrQDEDhAaN7jkTe4pzJ_qYFr-JiqM"
        const val HEADERS = (
                "apikey: $API_KEY, Authorization: Bearer $API_KEY, Content-Type: application/json"
                )
    }
}


