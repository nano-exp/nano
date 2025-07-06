import { defineStore } from 'pinia'
import { getRandomVv, searchVv } from '#@/services/vv.js'

export const useVvStore = defineStore('app', {
  state() {
    return {
      name: 'VV表情包',
      pageIndex: 1,
      pageSize: 10,
      keyword: '',
      list: [],
      loading: false,
      showLoadMore: true,
    }
  },
  actions: {
    async randomVv() {
      const store = this
      store.pageIndex = 1
      store.showLoadMore = false
      try {
        store.loading = true
        const vv = await getRandomVv()
        store.list = [vv]
      } finally {
        store.loading = false
      }
    },
    async onSearchVv(append = false) {
      const store = this
      if (!append) {
        store.pageIndex = 1
      } else {
        store.pageIndex = store.pageIndex + 1
      }
      try {
        store.loading = true
        const r = await searchVv({
          keyword: store.keyword,
          pageIndex: store.pageIndex,
          pageSize: store.pageSize,
        })
        if (append) {
          if (r.list.length) {
            store.list = store.list.concat(r.list)
          }
        } else {
          store.list = r.list
        }
        store.showLoadMore = r.list.length === store.pageSize
      } finally {
        store.loading = false
      }
    },
  },
})
