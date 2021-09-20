package com.joshmanisdabomb.lcc.mixin.content.common;

/*@Mixin(value = AddHillsLayer.class, priority = 500)
public class WastelandBiomeModifierMixin {

    @Inject(at = @At("HEAD"), method = "sample", cancellable = true)
    private void sample(LayerRandomnessSource rand, LayerSampler biomeSampler, LayerSampler noiseSampler, int chunkX, int chunkZ, CallbackInfoReturnable<Integer> info) {
        final int biomeId = biomeSampler.sample(chunkX, chunkZ);
        int noiseSample = noiseSampler.sample(chunkX, chunkZ);
        int processedNoiseSample = (noiseSample - 2) % 29;

        RegistryKey<Biome> key = BuiltinBiomes.fromRawId(biomeId);
        if (key == null) return;
        WeightedBiomePicker picker = InternalBiomeData.getOverworldHills().get(key);
        if (picker == null) return;

        if (key.equals(LCCBiomes.INSTANCE.getRegistryKey(LCCBiomes.INSTANCE.getWasteland_barrens()))) {
            if (rand.nextInt(3) == 0 || processedNoiseSample == 0) {
                int biomeReturn = InternalBiomeUtils.getRawId(picker.pickRandom(rand));

                if (biomeReturn != biomeId) {
                    /*int similarity = 0;

                    for (int i = -1; i <= 1; i += 2) {
                        if (BiomeLayers.areSimilar(biomeSampler.sample(chunkX + i, chunkZ), biomeId)) ++similarity;
                        if (BiomeLayers.areSimilar(biomeSampler.sample(chunkX, chunkZ + i), biomeId)) ++similarity;
                    }

                    if (similarity >= 3) {//
                        info.setReturnValue(biomeReturn);
                        info.cancel();
                        return;
                    //}
                }
            }

            info.setReturnValue(biomeId);
            info.cancel();
        }
    }

}
*/