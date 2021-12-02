package mrfinger.gothicgamemod;

import io.github.crucible.grimoire.common.api.grimmix.Grimmix;
import io.github.crucible.grimoire.common.api.grimmix.GrimmixController;
import io.github.crucible.grimoire.common.api.grimmix.lifecycle.IConfigBuildingEvent;

@Grimmix(id = "gothicgrimmix", name = "Gothic Grimmix")
public class GothicGrimmix extends GrimmixController {

    @Override
    public void buildMixinConfigs(IConfigBuildingEvent event) {
        event.createBuilder("examplemod/mixins.gothicgamemod.json")
        .mixinPackage("mrfinger.gothicgamemod.mixin")
        .commonMixins("common.*")
        .clientMixins("client.*")
        .refmap("@MIXIN_REFMAP@")
        .verbose(true)
        .required(true)
        .build();
    }

}
