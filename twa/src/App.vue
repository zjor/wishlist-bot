<script setup>
import {onMounted, ref} from 'vue'
import {logConfig} from "@/lib/config";
import {useWishlistStore} from "@/stores/wishlistStore";
import {useUiStateStore, PRIVATE_TAB, PUBLIC_TAB, SEARCH_TAB, PROFILE_TAB} from "@/stores/uiStateStore";
import PrivateWishList from "@/components/PrivateWishList.vue";
import PublicWishList from "@/components/PublicWishList.vue";
import PrivateWishlistItemDetails from "@/components/PrivateWishlistItemDetails.vue";
import PublicWishlistItemDetails from "@/components/PublicWishlistItemDetails.vue";
import ProfileView from "@/views/ProfileView.vue";
import SearchView from "@/views/SearchView.vue";
import api from "@/lib/api";

const wishlistStore = useWishlistStore()
const uiState = useUiStateStore()
const tab = ref(null)

async function onNewItemClicked() {
  window.Telegram?.WebApp?.close()
  await api.initItemCreation()
}

onMounted(async () => {
  logConfig()
  await wishlistStore.loadAllItems()
  await wishlistStore.loadProfile()
  await wishlistStore.loadProfileStats()
})

</script>

<template>
  <v-layout>
    <v-window v-model="uiState.currentTab" class="w-100 pb-12">
      <v-window-item value="private_tab" transition="none">
        <v-btn
            id="add-item"
            color="primary"
            icon="mdi-plus"
            @click="onNewItemClicked">
        </v-btn>
        <PrivateWishList v-if="!uiState.selectedItem" :items="wishlistStore.privateItems"/>
        <PrivateWishlistItemDetails v-else/>
      </v-window-item>
      <v-window-item value="public_tab" transition="none">
        <PublicWishList v-if="!uiState.selectedPublicItem" :items="wishlistStore.publicItems"/>
        <PublicWishlistItemDetails v-else/>
      </v-window-item>
      <v-window-item value="search_tab" transition="none">
        <SearchView/>
      </v-window-item>
      <v-window-item value="profile_tab" transition="none">
        <ProfileView/>
      </v-window-item>
    </v-window>

    <v-bottom-navigation grow v-model="tab" bg-color="black">
      <v-btn @click="uiState.setCurrentTab(PRIVATE_TAB)">
        <v-icon>mdi-format-list-checks</v-icon>
        My List
      </v-btn>

      <v-btn @click="uiState.setCurrentTab(PUBLIC_TAB)">
        <v-icon>mdi-earth</v-icon>
        Public
      </v-btn>

      <v-btn @click="uiState.setCurrentTab(SEARCH_TAB)">
        <v-icon>mdi-magnify</v-icon>
        Find by name
      </v-btn>

      <v-btn @click="uiState.setCurrentTab(PROFILE_TAB)">
        <v-icon>mdi-account</v-icon>
        Profile
      </v-btn>
    </v-bottom-navigation>
  </v-layout>
</template>

<style scoped>
#add-item {
  position: fixed;
  bottom: 0;
  right: 0;
  margin-right: 2em;
  margin-bottom: 5em;
  z-index: 1005;
}


</style>
