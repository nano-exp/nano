import { defineStore } from 'pinia'
import { searchVv } from '../services/vv.js'
import GUI from 'lil-gui'

export const useAdminVv = defineStore('app', {
    state() {
        return {
            gui: null,
            name: 'Admin VV',
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            keyword: '',
            list: [],
            loading: false,
            token: '',
        }
    },
    actions: {
        async changePage(newPageIndex) {
            this.pageIndex = newPageIndex
            await this.searchVvList()
        },
        createGui() {
            const gui = new GUI()
            gui.add(this, 'token').onChange((ev) => {
                this.saveToken(ev)
            })
            this.gui = gui
        },
        destroyGui() {
            const { gui } = this
            if (gui) {
                gui.destroy()
                this.gui = null
            }
        },
        saveToken(token) {
            localStorage.setItem('nano_token', token)
        },
        resurrectToken() {
            this.token = localStorage.getItem('nano_token') ?? ''
        },
        async searchVvList() {
            const { keyword, pageIndex, pageSize } = this
            try {
                this.loading = true
                const { list, totalCount } = await searchVv({ keyword, pageIndex, pageSize })
                list.sort((a, b) => a.id - b.id)
                this.list = list
                this.totalCount = totalCount
            } finally {
                this.loading = false
            }
        },
    }
})
