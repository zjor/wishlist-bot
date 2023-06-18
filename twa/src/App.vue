<script setup>
import {onMounted, ref} from 'vue'
import {logConfig} from "@/lib/config";
import {useWishlistStore} from "@/stores/wishlistStore";
import {useUiStateStore, PRIVATE_TAB, PUBLIC_TAB, SEARCH_TAB, PROFILE_TAB} from "@/stores/uiStateStore";
import WishList from "@/components/WishList.vue";
import WishlistItemDetails from "@/components/WishlistItemDetails.vue";

const wishlistStore = useWishlistStore()
const uiState = useUiStateStore()
const tab = ref(null)

onMounted(async () => {
  logConfig()
  await wishlistStore.loadAllItems()
})

</script>

<template>
  <v-layout>
    <v-window v-model="uiState.currentTab" class="w-100 pb-12">
      <v-window-item value="private_tab" transition="none">
        <WishList v-if="!uiState.selectedItem" :items="wishlistStore.privateItems"/>
        <WishlistItemDetails v-else/>
      </v-window-item>
      <v-window-item value="public_tab" transition="none">
        <WishList :items="wishlistStore.publicItems"/>
      </v-window-item>
      <v-window-item value="search_tab" transition="none">
        <v-container>
          Search will be there
        </v-container>
      </v-window-item>
      <v-window-item value="profile_tab" transition="none" style="background-color: fuchsia">
        <v-container>
          Profile goes here
        </v-container>
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

</style>
