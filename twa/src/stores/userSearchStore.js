import {ref} from 'vue'
import {defineStore} from 'pinia'

const INITIAL_ITEMS_STATE = {
  loading: true,
  items: []
}

export const useUserSearchStore = defineStore('userSearch', () => {
  const user = ref(undefined)

  const items = ref(INITIAL_ITEMS_STATE)

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

  return {
    user, items,
    setUser, setItems,
  }
})
