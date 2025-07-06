import { defineStore } from 'pinia'
import { createVv, deleteVv, searchVv } from '../services/vv.js'
import GUI from 'lil-gui'

export const useAdminVvStore = defineStore('app', {
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
      showNewModal: false,
      newData: {
        filename: '',
        getFile: null,
      },
      showDeleteModal: false,
      toDeleteId: null,
    }
  },
  actions: {
    async deleteVv() {
      const { token } = this
      await deleteVv({
        id: this.toDeleteId,
        token,
      })
      this.showDeleteModal = false
      await this.changePage(1)
    },
    async saveNewData() {
      const { token } = this
      await createVv({
        file: this.newData.getFile?.(),
        filename: this.newData.filename,
        token,
      })
      this.showNewModal = false
      await this.changePage(1)
    },
    async changePage(newPageIndex) {
      this.pageIndex = newPageIndex
      await this.searchVvList()
    },
    createGui() {
      const gui = new GUI()
      gui.add(this, 'token').onChange((ev) => this.saveToken(ev))
      gui.close()
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
        list.sort((a, b) => b.id - a.id)
        this.list = list
        this.totalCount = totalCount
      } finally {
        this.loading = false
      }
    },
  },
})
