import { defineStore } from 'pinia'
import GUI from 'lil-gui'
import { createVv, deleteVv, searchVv } from '#@/services/vv.js'

export const useVvAdminStore = defineStore('app', {
  state() {
    return {
      gui: null,
      name: 'VV Admin',
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
      const store = this
      await deleteVv({
        id: store.toDeleteId,
        token: store.token,
      })
      store.showDeleteModal = false
      await store.changePage(1)
    },
    async saveNewData() {
      const store = this
      await createVv({
        file: store.newData.getFile?.(),
        filename: store.newData.filename,
        token: store.token,
      })
      store.showNewModal = false
      await store.changePage(1)
    },
    async changePage(newPageIndex) {
      const store = this
      store.pageIndex = newPageIndex
      await store.searchVvList()
    },
    createGui() {
      const store = this
      const gui = new GUI()
      gui.add(store, 'token').onChange((ev) => store.saveToken(ev))
      gui.close()
      store.gui = gui
    },
    destroyGui() {
      const store = this
      const { gui } = this
      if (gui) {
        gui.destroy()
        store.gui = null
      }
    },
    saveToken(token) {
      localStorage.setItem('nano_token', token)
    },
    resurrectToken() {
      const store = this
      store.token = localStorage.getItem('nano_token') ?? ''
    },
    async searchVvList() {
      const store = this
      try {
        store.loading = true
        const { list, totalCount } = await searchVv({
          keyword: store.keyword,
          pageIndex: store.pageIndex,
          pageSize: store.pageSize,
        })
        list.sort((a, b) => b.id - a.id)
        store.list = list
        store.totalCount = totalCount
      } finally {
        store.loading = false
      }
    },
  },
})
