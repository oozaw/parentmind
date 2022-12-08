package com.capstone.parentmind.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.capstone.parentmind.data.remote.api.ApiService
import com.capstone.parentmind.data.remote.response.ArticlesItem

class ArticlePagingSource(
   private val apiService: ApiService,
   private val token: String,
   private val type: String
): PagingSource<Int, ArticlesItem>() {

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
      return try {
         val position = params.key ?: INITIAL_PAGE_INDEX
         val responseData = if (type.isEmpty()) {
            apiService.getAllArticlesPage(
               token = token,
               page = position,
               size = params.loadSize
            )
         } else {
            apiService.getArticlesTypePage(
               token = token,
               type = type,
               page = position,
               size = params.loadSize
            )
         }

         LoadResult.Page(
            data = responseData.articles,
            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
            nextKey = if (responseData.articles.isNullOrEmpty()) null else position + 1
         )
      } catch (e: Exception) {
         return LoadResult.Error(e)
      }
   }

   override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
      return state.anchorPosition?.let { anchorPosition ->
         val anchorPage = state.closestPageToPosition(anchorPosition)
         anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
      }
   }

   private companion object {
      const val INITIAL_PAGE_INDEX = 1
   }
}