import { defineStore } from 'pinia'

export const useAppStore = defineStore('app', {
    state() {
        return {
            name: 'Nano',
        }
    },
})
