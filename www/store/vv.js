import { defineStore } from 'pinia'
import { searchVv } from '../services/vv.js'

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
        async onSearchVv(append = false) {
            if (!append) {
                this.pageIndex = 1
            } else {
                this.pageIndex = this.pageIndex + 1
            }
            try {
                this.loading = true
                const r = await searchVv({
                    keyword: this.keyword,
                    pageIndex: this.pageIndex,
                    pageSize: this.pageSize,
                })
                if (append) {
                    if (r.list.length) {
                        this.list = this.list.concat(r.list)
                    }
                } else {
                    this.list = r.list
                }
                this.showLoadMore = r.list.length === this.pageSize;
            } finally {
                this.loading = false
            }
        },
    }
})
