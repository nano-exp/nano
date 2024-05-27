import { defineStore } from 'pinia'
import searchVv from '../apis/searchVv.js'

export const useVvStore = defineStore('app', {
    state() {
        return {
            name: 'VV表情包',
            pageIndex: 1,
            pageSize: 10,
            keyword: '',
            list: [],
            loading: false,
        }
    },
    actions: {
        async onSearchVv() {
            const { keyword, pageIndex, pageSize } = this
            try {
                this.loading = true
                this.list = await searchVv({ keyword, pageIndex, pageSize, })
            } finally {
                this.loading = false
            }
        },
    }
})
