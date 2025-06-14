<template>
  <ele-page flex-table hide-footer :multi-card="false">
    <ele-card flex-table :body-style="{ padding: 0, overflow: 'hidden' }">
      <pro-crud-builder v-model="config" />
    </ele-card>
  </ele-page>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import type { EleCrudProps } from 'ele-admin-plus/es/ele-app/plus';
  import ProCrudBuilder from '@/components/ProCrudBuilder/index.vue';

  defineOptions({ name: 'ListBuild' });

  const config = ref<EleCrudProps>({});
</script>
