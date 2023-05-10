<script setup>
import FaLinkIcon from "@/components/icons/FaLinkIcon.vue";
import {isValidUrl} from "@/lib/utils";

const props = defineProps({
  name: String,
  description: String,
  tags: Array[String],
  url: String
})
</script>

<template>
  <div class="item flex-row flex-grow-1">
    <div class="flex-col flex-grow-1">
      <div class="name">{{props.name}}</div>
      <div class="description">{{props.description}}</div>
      <div class="tags flex-row">
        <div class="tag" v-for="tag in props.tags" :key="tag">
          #{{tag}}
        </div>
      </div>
    </div>
    <div class="flex-col flex-center" v-if="isValidUrl(props.url)">
      <a :href="props.url">
        <FaLinkIcon class="icon"/>
      </a>
    </div>
  </div>
</template>

<style scoped>
.item {
  padding: 8px;
}

.name {
  font-weight: bolder;
}

.description {
  font-size: 0.9em;
}

.tags {
  gap: 8px;
  font-size: 0.9em;
}

.tag {
  padding: 1px 4px;
  background-color: wheat;
  border-radius: 4px;
}

.icon {
  width: 24px;
  height: 24px;
  padding: 2px;
  color: var(--vt-c-black-mute);
}

</style>
