import { ref } from 'vue'
import { defineStore } from 'pinia'

export const DEFAULT_IMAGE_URL = "https://res.cloudinary.com/zjor-storage/image/upload/v1684312235/wishlist_preview_vefxdb.png"

export const PRIVATE_TAB = "private_tab"
export const PUBLIC_TAB = "public_tab"
export const SEARCH_TAB = "search_tab"
export const PROFILE_TAB = "profile_tab"

export const useUiStateStore = defineStore('uiState', () => {
  const currentTab = ref(PRIVATE_TAB)
  const selectedItem = ref(undefined)
  const selectedPublicItem = ref(undefined)

  function setCurrentTab(value) {
    // TODO: validate
    currentTab.value = value
  }

  function setSelectedItem(value) {
    selectedItem.value = value
  }

  function setSelectedPublicItem(value) {
    selectedPublicItem.value = value
  }

  return {
    currentTab, selectedItem, selectedPublicItem,
    setCurrentTab, setSelectedItem, setSelectedPublicItem
  }
})
