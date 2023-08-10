import {ref} from 'vue'
import {defineStore} from 'pinia'

const INITIAL_ITEMS_STATE = {
  loading: true,
  items: []
}

export const useUserSearchStore = defineStore('userSearch', () => {
  const user = ref(undefined)
  const items = ref(INITIAL_ITEMS_STATE)
  const selectedItem = ref(undefined)

  function setUser(value) {
    user.value = value
    if (!value) {
      const { loading, items } = INITIAL_ITEMS_STATE
      setItems(loading, items)
    }
  }

  function setItems(loading = false, _items = []) {
    items.value = { loading, items: _items }
  }

  function setSelectedItem(value) {
    selectedItem.value = value
  }

  return {
    user, items, selectedItem,
    setUser, setItems, setSelectedItem
  }
})
